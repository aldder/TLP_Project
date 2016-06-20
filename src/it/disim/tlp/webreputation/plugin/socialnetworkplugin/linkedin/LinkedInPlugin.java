package it.disim.tlp.webreputation.plugin.socialnetworkplugin.linkedin;

import it.disim.tlp.webreputation.exceptions.pluginexceptions.QuotaExceededException;
import it.disim.tlp.webreputation.exceptions.pluginexceptions.ResourceURLFormatException;
import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.SocialNetworkPlugin;
import it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.authentication.AuthRequest;
import it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.authentication.AuthResponse;
import it.disim.tlp.webreputation.plugin.socialnetworkplugin.linkedin.authentication.LinkedInAuthRequest;
import it.disim.tlp.webreputation.plugin.socialnetworkplugin.linkedin.authentication.LinkedInAuthResponse;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class LinkedInPlugin implements
		SocialNetworkPlugin<LinkedInAuthRequest, LinkedInAuthResponse> {

	/* LinkedIn REST API Fixed Prefix */
	private final static String linkedInApiPrefix = "http://api.linkedin.com/v1/";

	@Override
	public List<AggregatorPost> getPosts(String resource, String type, Date from)
			throws QuotaExceededException {

		/* LINKEDIN AUTHENTICATION */
		LinkedInAuthRequest linkedInAuthRequest = new LinkedInAuthRequest();
		AuthRequest<LinkedInAuthRequest> authRequest = new AuthRequest<LinkedInAuthRequest>(
				linkedInAuthRequest);
		AuthResponse<LinkedInAuthResponse> authResponse = this
				.authentication(authRequest);

		List<AggregatorPost> result = new ArrayList<AggregatorPost>();
		try {
			if (type.toLowerCase().startsWith("group_posts")) {

				result.addAll(this.fetchGroupPostsFromLinkedIn(resource, from,
						null, authResponse));

			} else if (type.toLowerCase().startsWith("user_groups_posts")) {

				result.addAll(this.fetchAllUserGroupsPostsFromLinkedIn(
						resource, from, null, authResponse));

			}
		} catch (ParserConfigurationException | SAXException | IOException
				| ResourceURLFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * else if (type.toLowerCase().startsWith("company_updates")) {
		 * result.addAll(this.fetchCompanyUpdatesFromLinkedIn(resource, from,
		 * null, authResponse)); } else {
		 * result.addAll(this.fetchAllUserCompaniesUpdatesFromLinkedIn(resource,
		 * from, null, authResponse)); }
		 */
		return result;
	}

	/*
	 * private List<AggregatorPost>
	 * fetchAllUserCompaniesUpdatesFromLinkedIn(String resource, Date from,
	 * String to, AuthResponse<LinkedInAuthResponse> authResponse) { // TODO
	 * Auto-generated method stub return null; }
	 */

	/*
	 * private List<AggregatorPost> fetchCompanyUpdatesFromLinkedIn(String
	 * resource, Date from, Date to, AuthResponse<LinkedInAuthResponse>
	 * authResponse) {
	 * 
	 * 
	 * List<AggregatorPost> result = new ArrayList<AggregatorPost>();
	 * 
	 * 
	 * Pattern companyFieldPattern = Pattern.compile("company/([^?]+)"); Matcher
	 * companyFieldMatcher = companyFieldPattern.matcher(resource);
	 * System.out.println(resource);
	 * 
	 * if(companyFieldMatcher.find()) {
	 * 
	 * String companyFieldString = companyFieldMatcher.group().substring(8);
	 * 
	 * Pattern companyFieldIDPattern = Pattern.compile("^[0-9]+$"); Matcher
	 * companyFieldIDMatcher =
	 * companyFieldIDPattern.matcher(companyFieldString);
	 * if(companyFieldIDMatcher.find()){ // Get Company From ID
	 * 
	 * } else { // Get Company From Universal Name
	 * 
	 * }
	 * 
	 * 
	 * } return result; }
	 */

	private List<AggregatorPost> fetchAllUserGroupsPostsFromLinkedIn(
			String resource, Date from, Date to,
			AuthResponse<LinkedInAuthResponse> authResponse)
			throws ParserConfigurationException, SAXException, IOException,
			ResourceURLFormatException {

		/* RESULT AGGREGATOR POST LIST */
		List<AggregatorPost> result = new ArrayList<AggregatorPost>();

		/* LINKEDIN REST API REQUEST STRING */
		String groupsFromUserRequest = linkedInApiPrefix
				+ "people/~/group-memberships:(group:(name,site-group-url))";

		/* BUILD LINKEDIN REST API REQUEST */
		OAuthRequest getGroupsFromUser = new OAuthRequest(Verb.GET,
				groupsFromUserRequest);

		/* AUTHORIZE REQUEST */
		authResponse
				.getAuthResponse()
				.getLinkedInService()
				.signRequest(
						authResponse.getAuthResponse().getLinkedInOAuthToken(),
						getGroupsFromUser);

		/* SEND REQUEST */
		Response getPostsFromGroupResponse = getGroupsFromUser.send();

		/* RESPONSE DOM */
		DocumentBuilder documentBuilder;
		documentBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		InputSource inputSource = new InputSource();
		inputSource.setCharacterStream(new StringReader(
				getPostsFromGroupResponse.getBody()));
		Document document = documentBuilder.parse(inputSource);

		/* GET ALL GROUPS */
		NodeList groupNodes = document.getElementsByTagName("group");

		/* ITERATE THROUGH GROUPS */
		for (int i = 0; i < groupNodes.getLength(); i++) {

			/* GET SINGLE GROUP */
			Element element = (Element) groupNodes.item(i);

			String groupName = element.getElementsByTagName("name").item(0)
					.getTextContent();
			System.out.println(StringUtils.replaceChars(groupName, "àèéìòù",
					"aeeiou"));

			String groupURL = element.getElementsByTagName("site-group-url")
					.item(0).getTextContent();
			System.out.println(groupURL);

			result.addAll(fetchGroupPostsFromLinkedIn(groupURL, from, to,
					authResponse));
		}

		/*
		 * PRINT RETRIEVED POSTS Iterator<AggregatorPost> resultIterator =
		 * result.iterator(); while(resultIterator.hasNext()){ AggregatorPost
		 * post = resultIterator.next(); System.out.println("POST #" +
		 * result.indexOf(post) + " --> "+ post.getTitle()); }
		 */

		return result;
	}

	/* Check if retrieve post data should be encapsulated in a method */

	private List<AggregatorPost> fetchGroupPostsFromLinkedIn(String resource,
			Date from, Date to, AuthResponse<LinkedInAuthResponse> authResponse)
			throws ParserConfigurationException, SAXException, IOException,
			ResourceURLFormatException {

		/* RESULT AGGREGATOR POST LIST */
		List<AggregatorPost> result = new ArrayList<AggregatorPost>();

		/* CHECK URL RESOURCE FORMAT */
		Pattern groupIdPattern = Pattern.compile("gid=([^&]+)");
		Matcher groupIdMatcher = groupIdPattern.matcher(resource);

		/* CORRECT URL */
		if (groupIdMatcher.find()) {

			/* LINKEDIN REST API REQUEST STRING */
			String postsFromGroupRequest = linkedInApiPrefix
					+ "groups/"
					+ groupIdMatcher.group().substring(4)
					+ ":(posts:(title,creator,summary,creation-timestamp,likes,comments,site-group-post-url))";

			/* BUILD LINKEDIN REST API REQUEST */
			OAuthRequest getPostsFromGroup = new OAuthRequest(Verb.GET,
					postsFromGroupRequest);

			/* AUTHORIZE REQUEST */
			authResponse
					.getAuthResponse()
					.getLinkedInService()
					.signRequest(
							authResponse.getAuthResponse()
									.getLinkedInOAuthToken(), getPostsFromGroup);

			/* SEND REQUEST */
			Response getPostsFromGroupResponse = getPostsFromGroup.send();

			/* RESPONSE DOM */
			DocumentBuilder documentBuilder;
			documentBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(
					getPostsFromGroupResponse.getBody()));
			Document document = documentBuilder.parse(inputSource);

			/* GET ALL POSTS */
			NodeList groupNodes = document.getElementsByTagName("post");

			/* ITERATE THROUGH POSTS */
			for (int i = 0; i < groupNodes.getLength(); i++) {

				/* GET SINGLE POST */
				Element element = (Element) groupNodes.item(i);

				/* POST DATE AFTER FROM PARAMETER DATE */

				if (("" + new Timestamp(from.getTime()) + "").toString()
						.compareTo(
								element.getElementsByTagName(
										"creation-timestamp").item(0)
										.getTextContent()) > 0
						&& (to == null || ("" + new Timestamp(to.getTime()) + "")
								.toString().compareTo(
										element.getElementsByTagName(
												"creation-timestamp").item(0)
												.getTextContent()) < 0)) {

					/* NEW POST */
					AggregatorPost post = new AggregatorPost();

					/* POST TITLE */
					String postTitle = StringUtils.replaceChars(
							element.getElementsByTagName("title").item(0)
									.getTextContent(), "àèéìòù", "aeeiou")
							.replaceAll("\\s+", "");
					post.setTitle(postTitle);

					/* POST AUTHOR */
					Element postCreatorInfo = (Element) element
							.getElementsByTagName("creator").item(0); // Ignore
																		// Multiple
																		// Authors
					String postCreator = postCreatorInfo
							.getElementsByTagName("first-name").item(0)
							.getTextContent()
							+ " "
							+ postCreatorInfo.getElementsByTagName("last-name")
									.item(0).getTextContent();
					post.setAuthor(postCreator);

					/* POST DATE */
					Date postCreationDate = new Date((new Timestamp(
							Long.parseLong(element
									.getElementsByTagName("creation-timestamp")
									.item(0).getTextContent()))).getTime());
					post.setDate(postCreationDate);

					/* POST TEXT */
					String postContent = StringUtils.replaceChars(
							element.getElementsByTagName("summary").item(0)
									.getTextContent(), "àèéìòù", "aeeiou")
							.replaceAll("\\s+", "");
					post.setText(postContent);

					/* POST SOURCE */
					post.setSource("LinkedIn");

					/* POST VISIBILITY */
					int postVisibility = Integer.parseInt(element
							.getElementsByTagName("comments").item(0)
							.getAttributes().item(0).getNodeValue())
							+ Integer.parseInt(element
									.getElementsByTagName("likes").item(0)
									.getAttributes().item(0).getNodeValue());
					post.setVisibility(postVisibility);

					/* POST LINK */
					String postLink = element
							.getElementsByTagName("site-group-post-url")
							.item(0).getTextContent();
					post.setLink(postLink);

					/* ADD POST */
					result.add(post);
				}
			}
		} else {
			/* Wrong URL format */
			throw new ResourceURLFormatException(
					"Wrong Resource URL Format ( Missing Group id )");
		}
		return result;

	}

	@Override
	public List<AggregatorPost> getPosts(String resource, String type,
			Date from, Date to) throws QuotaExceededException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthResponse<LinkedInAuthResponse> authentication(
			AuthRequest<LinkedInAuthRequest> request) {
		/* Creates New LinkedIn Service Stub */
		OAuthService linkedInService = new ServiceBuilder()
				.provider(LinkedInApi.class)
				.apiKey(request.getAuthRequest().getApiKey())
				.apiSecret(request.getAuthRequest().getSecretKey()).build();
		/* Obtains New LinkedIn Authorization Token */
		Token linkedInOAuthToken = new Token(request.getAuthRequest()
				.getoAuthUserToken(), request.getAuthRequest()
				.getoAuthUserSecret());
		/* Returns New LinkedInAuthResponse */
		return new AuthResponse<LinkedInAuthResponse>(new LinkedInAuthResponse(
				linkedInService, linkedInOAuthToken));
	}
}
